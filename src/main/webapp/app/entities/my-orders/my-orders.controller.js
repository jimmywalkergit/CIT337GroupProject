(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyOrdersController', MyOrdersController);

    MyOrdersController.$inject = ['$scope', '$state', 'MyOrders'];

    function MyOrdersController ($scope, $state, MyOrders) {
        var vm = this;
        
        vm.myOrders = [];

        loadAll();

        function loadAll() {
            MyOrders.query(function(result) {
                vm.myOrders = result;
            });
        }
    }
})();
