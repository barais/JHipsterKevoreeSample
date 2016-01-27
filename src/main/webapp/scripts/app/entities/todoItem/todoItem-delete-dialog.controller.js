'use strict';

angular.module('diversescholApp')
	.controller('TodoItemDeleteController', function($scope, $uibModalInstance, entity, TodoItem) {

        $scope.todoItem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TodoItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
