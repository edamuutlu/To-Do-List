var myApp = angular.module("myApp", []);

myApp.controller("rootController", ["$scope", "$http", function($scope) {
	$scope.openModal = function() {
		$('#myModal').modal('show');
	};
	$scope.closeModal = function() {
		$('#myModal').modal('hide');
	};
}]);

myApp.controller("LoginController", ["$scope", function($scope) {

	$scope.login = function() {
		$scope.msg = "Success Login!";
	}

}]);

myApp.controller("registerFormController", ["$scope", function($scope) {

	$scope.register = function() {
		$scope.msg = "This is a warning alert—check it out!";
	}

	var today = new Date();
	today.setFullYear(today.getFullYear() - 18); // Bugünden 18 yıl önceki tarih

	var birthInput = document.getElementById("birth");
	birthInput.setAttribute("max", today.toISOString().split("T")[0]);

	$scope.validatePassword = function() {
		$scope.passwordError = checkPasswordStrength($scope.password);
	}

}]);

myApp.controller("customerEditFormController", ["$scope", function($scope) {

	$scope.register = function() {
		$scope.msg = "This is a warning alert—check it out!";
	}

	$scope.firstname = firstname;
	$scope.lastname = lastname;
	$scope.tc = tc;
	$scope.email = email;
	$scope.username = username;
	$scope.password = password;
	$scope.birth = birth;
	$scope.phoneNumber = phoneNumber;
	$scope.gender = gender;

	var today = new Date();
	today.setFullYear(today.getFullYear() - 18); // Bugünden 18 yıl önceki tarih

	var birthInput = document.getElementById("birth");
	birthInput.setAttribute("max", today.toISOString().split("T")[0]);

	$scope.validatePassword = function() {
		$scope.passwordError = checkPasswordStrength($scope.password);
	}

}]);

function checkPasswordStrength(password) {
	const regexLowercase = /[a-z]/;
	const regexUppercase = /[A-Z]/;
	const regexNumber = /[0-9]/;
	const regexSpecial = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/;

	if (password.length < 8) {
		return 'Password must be at least 8 characters long.';
	}

	if (!regexLowercase.test(password)) {
		return 'Password must contain at least one lowercase letter.';
	}

	if (!regexUppercase.test(password)) {
		return 'Password must contain at least one uppercase letter.';
	}

	if (!regexNumber.test(password)) {
		return 'Password must contain at least one number.';
	}

	if (!regexSpecial.test(password)) {
		return 'Password must contain at least one special character.';
	}

	return null; // No errors
}

function togglePasswordVisibility() {
	var password = document.getElementById("password");
	var hideIcon = document.getElementById("hideIcon");
	var showIcon = document.getElementById("showIcon");

	// Check the current type of the password input
	if (password.type === "password") {
		password.type = "text";
		showIcon.style.display = "none";
		hideIcon.style.display = "inline-block";
	} else {
		password.type = "password";
		showIcon.style.display = "inline-block";
		hideIcon.style.display = "none";
	}
}

// To-do list
function addItem(text, status, id, noUpdate) {
	var c = status === "done" ? "danger" : "";
	var item =
		'<tr"' +
		'" class="animated flipInX ' +
		c +
		'"><td>1</td>' +
		'<td>To do list Ekranı</td>' +
		'<td>Ekranda listler arasında geçişler olucak</td>' +
		'<td>Orta Seviye</td>' +
		'<td>16.01.2023</td>' +
		'<td><button class="mx-auto"><a href="#"><i class="bx bx-check check"></i></a></button></td>' +
		'<td><button class="mx-auto"><a href="#"><i class="bx bxs-edit edit"></i></a></button></td>' +
		'<td><button class="mx-auto"><a href="#"><i class="bx bx-trash trash"></i></a></button></td>' +
		'</tr>';

	var isError = $(".form-control").hasClass("hidden");

	if (text === "") {
		$(".err")
			.removeClass("hidden")
			.addClass("animated bounceIn");
	} else {
		$(".err").addClass("hidden");
		$(".todo-list").append(item);
	}

	$(".refresh").removeClass("hidden");

	$(".no-items").addClass("hidden");

	$(".form-control")
		.val("")
		.attr("placeholder", "✍ Görev ekleyin...");
	setTimeout(function() {
		$(".todo-list tr").removeClass("animated flipInX");
	}, 500);

	if (!noUpdate) {
		pushToState(text, "new", id);
	}
}

function refresh() {
	$(".todo-list tr").each(function(i) {
		$(this)
			.delay(70 * i)
			.queue(function() {
				$(this).addClass("animated bounceOutLeft");
				$(this).dequeue();
			});
	});

	setTimeout(function() {
		$(".todo-list tr").remove();
		$(".no-items").removeClass("hidden");
		$(".err").addClass("hidden");
	}, 800);
}
// To do list


$(function() {
	var err = $(".err"),
		formControl = $(".form-control"),
		isError = formControl.hasClass("hidden");

	if (!isError) {
		formControl.blur(function() {
			err.addClass("hidden");
		});
	}

	$(".add-btn").on("click", function() {
		var itemVal = $(".form-control").val();
		addItem(itemVal);
		formControl.focus();
	});

	$(".refresh").on("click", refresh);

	$(".todo-list").on("click", ".close", function() {
		var box = $(this)
			.parent()
			.parent();

		if ($(".todo-list li").length == 1) {
			box.removeClass("animated flipInX").addClass("animated bounceOutLeft");
			setTimeout(function() {
				box.remove();
				$(".no-items").removeClass("hidden");
				$(".refresh").addClass("hidden");
			}, 500);
		} else {
			box.removeClass("animated flipInX").addClass("animated bounceOutLeft");
			setTimeout(function() {
				box.remove();
			}, 500);
		}

		deleteTodo(box.data().id)
	});

	$(".form-control").keypress(function(e) {
		if (e.which == 13) {
			var itemVal = $(".form-control").val();
			addItem(itemVal);
		}
	});
	$(".todo-list").sortable();
	$(".todo-list").disableSelection();
});

document.addEventListener('DOMContentLoaded', function() {
  var buttons = document.querySelectorAll('button[data-complete="true"]');
  console.log(buttons);

  buttons.forEach(function(button) {
    markAsCompleted(button);
  });
});

function markAsCompleted(button) {
	var row = button.parentElement.parentElement;

	var isActive = row.classList.toggle('active');

	// Ikonun değişmesi için
	var iconCheck = button.querySelector('.bx-check');
	var iconx = button.querySelector('.bx-x');

	if (iconCheck) {
		// Eğer active durumdaysa, ikonu değiştir
		console.log(iconCheck);
		iconCheck.classList.replace('bx-check', 'bx-x');
	}
	if (iconx) {
		// Değilse, ikonu eski haline getir
		console.log(iconx);
		iconx.classList.replace('bx-x', 'bx-check');
	}
};

function openUpdateModal(button) {
	$('#updateModal').modal('show');
    var itemId = button.getAttribute('data-itemId');
    var taskName = button.getAttribute('data-taskName');
    var description = button.getAttribute('data-description');
    
    console.log("Item ID: ", itemId);
    console.log("Task Name: ", taskName);
    console.log("Description: ", description);
    
    console.log(document.getElementById('updateForm'));

    // Verileri HTML içine gömme
    document.getElementById('updateTaskName').value = taskName;
    document.getElementById('updateDescription').value = description;
	document.getElementById('updateForm').action = '/todo/' + itemId;
}

function closeUpdateModal() {
	$('#updateModal').modal('hide');
};