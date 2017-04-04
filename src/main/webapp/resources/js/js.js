function validate() {
    if ($('#register_password').val() != $('#register_password_again').val()) {
        alert('The password must be same');
        return false;
    }
    return true;
}

function logout(path, csfs) {
    post(path, csfs);
}

function post(path, params, method) {
    method = method || 'post'; 
    
    var form = document.createElement('form');
    form.setAttribute('method', method);
    form.setAttribute('action', path);

    for (var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement('input');
            hiddenField.setAttribute('type', 'hidden');
            hiddenField.setAttribute('name', key);
            hiddenField.setAttribute('value', params[key]);

            form.appendChild(hiddenField);
         }
    }

    document.body.appendChild(form);
    form.submit();
}