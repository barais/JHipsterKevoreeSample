'use strict';

angular.module('diversescholApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


