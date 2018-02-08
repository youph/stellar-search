#!/usr/bin/env bash

usage() {
  cat - >&2 <<EOF
NAME
    stack.sh

DESCRIPTION
    Bring the stellar stack up or down. Simple wrapper around docker-compose which
    provides a default project name (stellar-search) and only launches the dependant
    services need by the search spring app needed for development. To bring
    up the spring app in a docker container in addition to the dependant service, you
    can specify with the `--all` option.

SYNOPSIS
    stack.sh [-h|--help]
    stack.sh [-v|--verbose]
             [-a|--all]
             [-p|--project-name <name>]
             [--]
             <COMMAND> [ARGS...]

ARGUMENTS
  COMMAND
          docker compose command. E.g. up or down
  ARGS
          Additional arguments to pass to docker-compose command (e.g --detach, --build)

OPTIONS
  -h, --help
          Prints this and exits

  -v, --verbose
          Enable debug messages

  -a, --all
          bring up all services including the app

  -p, --project-name <name>
          Project name to use (defaults to stellar-search)

  --
          Specify end of options; useful if the first non option
          argument starts with a hyphen

EOF
}

## logging setup

enable_colourised_logging() {
    red="\033[1;31m"        # error / fatal
    green="\033[1;32m"      # info
    yellow="\033[1;33m"     # warn
    blue="\033[1;34m"       # debug
    reset="\033[0m"
}

if [ -t 2 ]; then
    # stderr is connected to a terminal so enable colourised logging
    enable_colourised_logging
fi

# log formatter
prog_name="$(basename "$0")"
date_format="%F %T %Z"   # YYYY-MM-DD HH:MM:SS ZZZZ
stderr() {
    local prefix="${prog_name}: $(date "+${date_format}") $prefix"
    local i
    if [ "$#" -ne 0 ]; then
        for i; do           # read from args
            echo -e "${prefix}${i}${reset}" >&2
        done
    else
        while read i; do    # read from stdin
            echo -e "${prefix}${i}${reset}" >&2
        done
    fi
}

# log levels. Usage either:
#   <level> "line 1" "line 2" "line 3" ;
#   or
#   process | <level> ;
debug() { [ -z "$verbose" ] || { local prefix="${blue}Debug:${reset} " ; stderr "$@" ; } ; }
info() { local prefix="${green} Info:${reset} " ; stderr "$@" ; }
warn() { local prefix="${yellow} Warn:${reset} " ; stderr "$@" ; }
error() { local prefix="${red}Error:${reset} " ; stderr "$@" ; }
fatal() { local prefix="${red}Fatal:${reset} " ; stderr "$@" ; exit 1 ; }

## command line arg processing

# defaults
project_name=stellar-search

# There are several ways to parse cmdline args (e.g. GNU getopt (not portable) vs BSD (OSX) getopt
# vs getopts) - all shit. This solution is both portable, allows for both short/long options,
# handles whitespace, handles optional-option arguments ;), and frankly doesn't require much code
# bloat compared with alternatives.

# For long option processing. we can't use process substitution as OPTIND
# does not propagate across sub shells so we reassign output in OPTARG
next_arg() {
    if [[ $OPTARG == *=* ]]; then
        # for cases like '--opt=foo'
        OPTARG="${OPTARG#*=}"
    else
        # for cases like '--opt foo'
        OPTARG="${args[$OPTIND]}"
        OPTIND=$((OPTIND + 1))
    fi
}

# ':' means preceding option character expects one argument, except
# first ':' which make getopts run in silent mode. We handle errors with
# wildcard case catch. Long options are considered as the '-' character
optspec=":hvap:-:"
args=("" "$@")  # dummy first element so $1 and $args[1] are aligned
while getopts "$optspec" optchar; do
    case "$optchar" in
        h) usage; exit 0 ;;
        v) verbose=1 ;;
        a) all=1 ;;
        p) project_name="$OPTARG" ;;
        -) # long option processing
            case "$OPTARG" in
                help)
                    usage; exit 0 ;;
                verbose)
                    verbose=1 ;;
                all)
                    all=1 ;;
                project-name|project-name=*) next_arg
                    project_name="$OPTARG" ;;
                -) break ;;
                *) fatal "Unknown option '--${OPTARG}'" "see '${prog_name} --help' for usage" ;;
            esac
            ;;
        *) fatal "Unknown option: '-${OPTARG}'" "See '${prog_name} --help' for usage" ;;
    esac
done

shift $((OPTIND-1))

if [ "$#" -eq 0 ]; then
    fatal "Did not specify COMMAND" "See '${prog_name} --help' for usage"
fi

## end of command lne processing

basedir="$( cd "$(dirname "$0")" ; pwd)"

# needed to pick up .env file
cd "$basedir"

cmd_prefix=(docker-compose --project-name "$project_name")

if [ "$1" = up ]; then
    if [ -n "$all" ]; then
        info "Starting all services"
    else
        services=(elasticsearch kibana db adminer)
        info "Starting services: ${services[*]}"
    fi
fi

cmd=("${cmd_prefix[@]}" "${@}" "${services[@]}")
info "running ${cmd[*]}"

"${cmd[@]}"
