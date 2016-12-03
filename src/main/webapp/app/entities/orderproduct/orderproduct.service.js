(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('Orderproduct', Orderproduct);

    Orderproduct.$inject = ['$resource'];

    function Orderproduct ($resource) {
        var resourceUrl =  'api/orderproducts/:id';

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
