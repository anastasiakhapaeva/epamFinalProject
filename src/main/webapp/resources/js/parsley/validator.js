/**
 * Created by Roman on 24.12.2016.
 */
window.Parsley.addValidator('login', {
    validateString: function (value, regexp) {
        return regexp.test(value);
    },
    requirementType: 'regexp',
    messages: {
        en: 'Invalid login. Login must have more than 3 symbols, starts with latin letter,' +
        'may contains _',
        ru: "Недопустимый логин. Логин должен иметь больше 3 символов, " +
        "начинаться с латинской буквы, может содержать _"
    }
});
window.Parsley.addValidator('password', {
    validateString: function (value, regexp) {
        return regexp.test(value);
    },
    requirementType: 'regexp',
    messages: {
        en: 'Invalid password. Password must have more than 5 symbols, contains at least 1 letter in each case ' +
        'and at least one digit.',
        ru: "Недопустимый пароль. Пароль должен иметь более 5 символов, сожержать хотя бы одну букву в каждом регистре " +
        " и хотя бы одну цифру."
    }
});
window.Parsley.addValidator('pass', {
    validateString: function (value, id) {
        return value == document.getElementById(id).value;
    },
    requirementType: 'string',
    messages: {
        en: 'Password\'s doesn\'t match.',
        ru: "Пароли не совпадают."
    }
});