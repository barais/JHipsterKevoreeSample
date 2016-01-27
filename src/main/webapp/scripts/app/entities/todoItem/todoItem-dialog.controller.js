'use strict';

angular.module('diversescholApp').controller('TodoItemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TodoItem',
        function($scope, $stateParams, $uibModalInstance, entity, TodoItem) {

        $scope.todoItem = entity;
        $scope.load = function(id) {
            TodoItem.get({id : id}, function(result) {
                $scope.todoItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('diversescholApp:todoItemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.todoItem.id != null) {
                TodoItem.update($scope.todoItem, onSaveSuccess, onSaveError);
            } else {
                TodoItem.save($scope.todoItem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
}]);
