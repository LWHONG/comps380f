function validateForPoll() {
    if (($('#question').val() == '') || 
        ($('#option_a').val() == '') || 
        ($('#option_b').val() == '') || 
        ($('#option_c').val() == '') || 
        ($('#option_d').val() == '')) {
        alert('All field of the poll cannot be empty.');
        return false;
    }
    return true;
}

function validate() {
    if ($('#password').val() == '') {
        alert('The password cannot be empty.');
        return false;
    }else if ($('#password').val() != $('#password_confirm').val()) {
        alert('The password must be same.');
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