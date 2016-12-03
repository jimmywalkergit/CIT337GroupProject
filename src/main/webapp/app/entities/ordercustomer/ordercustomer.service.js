(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('Ordercustomer', Ordercustomer);

    Ordercustomer.$inject = ['$resource'];

    function Ordercustomer ($resource) {
        var resourceUrl =  'api/ordercustomers/:id';

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
