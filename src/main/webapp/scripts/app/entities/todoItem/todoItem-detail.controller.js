'use strict';

angular.module('diversescholApp')
    .controller('TodoItemDetailController', function ($scope, $rootScope, $stateParams, entity, TodoItem) {
        $scope.todoItem = entity;
        $scope.load = function (id) {
            TodoItem.get({id: id}, function(result) {
                $scope.todoItem = result;
            });
        };
        var unsubscribe = $rootScope.$on('diversescholApp:todoItemUpdate', function(event, result) {
            $scope.todoItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
