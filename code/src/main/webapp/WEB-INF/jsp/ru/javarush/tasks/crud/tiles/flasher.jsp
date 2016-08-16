<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty msg}">
    <div class="alert alert-${css} alert-dismissible flasher" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Закрыть">
            <span aria-hidden="true">&times;</span>
        </button>
        <script>
            $('button.close').on("click", function () {
                $(this).parent('div').dequeue(); // отключить delay
                $(this).parent('div').fadeOut(1000);
            });
            $('button.close').parent('div').delay(2500).fadeOut(1000);
        </script>
        <strong>${msg}</strong>
    </div>
</c:if>