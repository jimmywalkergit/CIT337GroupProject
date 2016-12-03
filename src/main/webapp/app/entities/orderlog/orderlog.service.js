(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('Orderlog', Orderlog);

    Orderlog.$inject = ['$resource'];

    function Orderlog ($resource) {
        var resourceUrl =  'api/orderlogs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
