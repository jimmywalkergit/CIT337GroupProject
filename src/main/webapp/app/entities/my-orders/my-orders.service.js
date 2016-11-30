(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('MyOrders', MyOrders);

    MyOrders.$inject = ['$resource'];

    function MyOrders ($resource) {
        var resourceUrl =  'api/my-orders/:id';

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
