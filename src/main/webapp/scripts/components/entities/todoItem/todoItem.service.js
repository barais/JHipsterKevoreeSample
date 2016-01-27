'use strict';

angular.module('diversescholApp')
    .factory('TodoItem', function ($resource, DateUtils) {
        return $resource('api/todoItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    return angular.toJson(data);
                }
            }
        });
    });
