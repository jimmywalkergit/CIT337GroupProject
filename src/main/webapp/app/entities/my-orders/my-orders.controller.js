(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyOrdersController', MyOrdersController);

    MyOrdersController.$inject = ['$scope', '$state', 'MyOrders','Testproduct','MyProduct', 'Testorder', 'Ordercustomer','Orderlog','Orderproduct'];

    function MyOrdersController ($scope, $state, MyOrders, Testproduct, MyProduct, Testorder, Ordercustomer, Orderlog, Orderproduct) {
        var vm = this;

        vm.myOrders = [];
        vm.testproducts = Testproduct.query();
        vm.myproducts = MyProduct.query();
        vm.testorders = Testorder.query();
        vm.ordercustomers = Ordercustomer.query();
        vm.orderlogs = Orderlog.query();
        vm.orderproducts = Orderproduct.query();

        loadAll();

        function loadAll() {
            MyOrders.query(function(result) {
                vm.myOrders = result;
            });
        }
    }
})();
