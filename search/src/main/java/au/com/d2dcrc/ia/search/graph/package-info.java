/**
 * Defines classes and interfaces specific to the extended property graph model
 * (EPGM).
 * <p>
 * Semantics: The MutableEpgXXX interfaces denote objects with both getter and
 * setter methods, which are therefore mutable. The EpgXXX interfaces denote
 * objects with only getter methods, and these should be treated as immutable
 * even if the underlying implementation is actually mutable. In particular, it
 * is semantically invalid (although syntactically possible) to modify the
 * properties obtained through the EpgXXX interfaces.
 * </p>
 * 
 * @see <a href="https://dbs.uni-leipzig.de/file/EPGM.pdf">EPGM.pdf</a>
 */
package au.com.d2dcrc.ia.search.graph;