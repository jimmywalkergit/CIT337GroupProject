(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('MyCart', MyCart);

    MyCart.$inject = ['$resource'];

    function MyCart ($resource) {
        var resourceUrl =  'api/my-carts/:id';

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
