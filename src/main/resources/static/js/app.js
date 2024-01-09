var myApp = angular.module("myApp", []);

myApp.controller("rootController", ["$scope", "$http", function($scope) {
}]);

myApp.controller("LoginController", ["$scope", function($scope) {

	$scope.login = function() {
		$scope.msg = "Success Login!";
	}

}])

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