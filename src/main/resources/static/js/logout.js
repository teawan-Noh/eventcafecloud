function logout() {
    $.ajax({
        type: "GET",
        url: "/userLogout",
        success: function (response) {
            window.location.href = '/';
        }
    });
}