(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('Testorder', Testorder);

    Testorder.$inject = ['$resource'];

    function Testorder ($resource) {
        var resourceUrl =  'api/testorders/:id';

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
