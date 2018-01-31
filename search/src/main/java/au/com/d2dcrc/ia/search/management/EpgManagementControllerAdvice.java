package au.com.d2dcrc.ia.search.management;

import au.com.d2dcrc.ia.search.response.BaseControllerAdvice;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// XXX Removing the package/class restriction(s) entirely breaks EpgReferenceModelTest.testDeserialize().
@ControllerAdvice(assignableTypes = EpgManagementController.class)
public class EpgManagementControllerAdvice extends BaseControllerAdvice {

    @ExceptionHandler({ EntityExistsException.class, EntityNotFoundException.class })
    @ResponseBody
    ResponseEntity<?> handleEntityExistsException(HttpServletRequest request, Throwable ex) {
        return errorResponse(request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseEntity<?> handleException(HttpServletRequest request, Throwable ex) {
        return errorResponse(request, ex);
    }

}