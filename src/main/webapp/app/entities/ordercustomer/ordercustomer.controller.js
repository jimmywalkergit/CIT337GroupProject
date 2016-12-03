(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrdercustomerController', OrdercustomerController);

    OrdercustomerController.$inject = ['$scope', '$state', 'Ordercustomer'];

    function OrdercustomerController ($scope, $state, Ordercustomer) {
        var vm = this;
        
        vm.ordercustomers = [];

        loadAll();

        function loadAll() {
            Ordercustomer.query(function(result) {
                vm.ordercustomers = result;
            });
        }
    }
})();
