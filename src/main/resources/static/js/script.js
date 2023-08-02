function checkPwdValues() {
  let currentPassword = document.getElementById("currentPassword").value;
  let newPassword = document.getElementById("newPassword").value;
  let confirmPassword = document.getElementById("confirmPassword").value;

  if (newPassword != confirmPassword) {
    alert("two.......");
    document.querySelector(".twoPwdError").innerHTML =
      "Please enter the same value in new password and confirm password!";
    return false;
  }

  if (newPassword.trim() == "") {
    alert("new password :" + newPassword);
    document.querySelector(".newpasswordError").innerHTML =
      "Please not be blank for confirm Password!";
    $("newPassword").focus();
    return false;
  }
  if (newPassword.trim() == "") {
    alert("confirm Password :" + confirmPassword);
    document.querySelector(".confirmpasswordError").innerHTML =
      "Please not be blank for confirm Password!";
    $("confirmPassword").focus();
    return false;
  } else {
    alert("ajax calling......");

    $.ajax({
      type: "POST",
      url: "http://localhost:8080/admin/changePassword",
      contentType: "application/json",
      data: {
        currentPassword: "currentPassword",
        newPassword: "newPassword",
        confirmPassword: "confirmPassword",
      },
      dataType: "json",
      success: function () {
        alert("success.......................");
      },
      error: function () {
        alert("error.............................");
      },
    });
    return true;
  }
}
