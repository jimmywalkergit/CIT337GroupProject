(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('CustomerController', CustomerController);

    CustomerController.$inject = ['$scope', '$state', 'Customer'];

    function CustomerController ($scope, $state, Customer) {
        var vm = this;
        
        vm.customers = [];

        loadAll();

        function loadAll() {
            Customer.query(function(result) {
                vm.customers = result;
            });
        }
    }
})();
