(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCartController', MyCartController);

    MyCartController.$inject = ['$scope', '$state', 'MyCart','Testproduct','MyProduct'];

    function MyCartController ($scope, $state, MyCart, Testproduct, MyProduct) {
        var vm = this;
        vm.myCarts = [];
        vm.testproducts = Testproduct.query();
        vm.myproducts = MyProduct.query();


        loadAll();

        function loadAll() {
            MyCart.query(function(result) {
                vm.myCarts = result;
            });
        }
    }
    function getTotal(){



    }

})();
