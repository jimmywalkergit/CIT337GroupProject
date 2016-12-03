(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyOrdersController', MyOrdersController);

    MyOrdersController.$inject = ['$scope', '$state', 'MyOrders','Testproduct','MyProduct', 'Testorder'];

    function MyOrdersController ($scope, $state, MyOrders, Testproduct, MyProduct, Testorder) {
        var vm = this;

        vm.myOrders = [];
        vm.testproducts = Testproduct.query();
        vm.myproducts = MyProduct.query();
        vm.testorders = Testorder.query();


        loadAll();

        function loadAll() {
            MyOrders.query(function(result) {
                vm.myOrders = result;
            });
        }
    }
})();
