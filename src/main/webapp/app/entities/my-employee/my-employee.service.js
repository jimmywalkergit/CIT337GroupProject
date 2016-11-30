(function() {
    'use strict';
    angular
        .module('cit337ProjectApp')
        .factory('MyEmployee', MyEmployee);

    MyEmployee.$inject = ['$resource'];

    function MyEmployee ($resource) {
        var resourceUrl =  'api/my-employees/:id';

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
