(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('MyCustomer', MyCustomer);

    MyCustomer.$inject = ['$resource'];

    function MyCustomer ($resource) {
        var resourceUrl =  'api/my-customers/:id';

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
