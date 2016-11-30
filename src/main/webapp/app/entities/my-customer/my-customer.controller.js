(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCustomerController', MyCustomerController);

    MyCustomerController.$inject = ['$scope', '$state', 'MyCustomer'];

    function MyCustomerController ($scope, $state, MyCustomer) {
        var vm = this;
        
        vm.myCustomers = [];

        loadAll();

        function loadAll() {
            MyCustomer.query(function(result) {
                vm.myCustomers = result;
            });
        }
    }
})();
