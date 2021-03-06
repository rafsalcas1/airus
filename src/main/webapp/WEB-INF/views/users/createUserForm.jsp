<airus:layout pageName="users">
    <h2>
        <c:if test="${user['new']}">New </c:if> User
    </h2>
    <form:form modelAttribute="user" class="form-horizontal" id="add-user-form">
        <div class="form-group has-feedback">
            <airus:inputField label="Nombre" name="nombre"/>
            <airus:inputField label="Apellidos" name="apellidos"/>
            <airus:inputField label="Fecha Nacimiento" name="fechaNacimiento"/>
            <airus:inputField label="Correo" name="correo"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Add User</button>
            </div>
        </div>
    </form:form>
</airus:layout>
