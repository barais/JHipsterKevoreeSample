'use strict';

angular.module('diversescholApp')
    .factory('TodoItemSearch', function ($resource) {
        return $resource('api/_search/todoItems/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
