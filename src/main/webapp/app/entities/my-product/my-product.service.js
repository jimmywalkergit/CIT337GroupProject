(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('MyProduct', MyProduct);

    MyProduct.$inject = ['$resource'];

    function MyProduct ($resource) {
        var resourceUrl =  'api/my-products/:id';

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
