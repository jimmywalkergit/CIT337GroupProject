(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('Testproduct', Testproduct);

    Testproduct.$inject = ['$resource'];

    function Testproduct ($resource) {
        var resourceUrl =  'api/testproducts/:id';

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
