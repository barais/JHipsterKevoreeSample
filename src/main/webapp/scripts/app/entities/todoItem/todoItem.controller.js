'use strict';

angular.module('diversescholApp')
    .controller('TodoItemController', function ($scope, $state, TodoItem, TodoItemSearch, ParseLinks) {

        $scope.todoItems = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            TodoItem.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.todoItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TodoItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.todoItems = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.todoItem = {
                content: null,
                endDate: null,
                done: null,
                id: null
            };
        };
    });
