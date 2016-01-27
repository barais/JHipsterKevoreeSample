'use strict';

angular.module('diversescholApp')
    .controller('MainController', function ($scope, Principal,$http) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            $scope.foo = function(){
                console.log("ok clic");
                $http.get('/toto', function(data){

                });
            }

        });
    });
