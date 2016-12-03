(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyProductController', MyProductController);

    MyProductController.$inject = ['$scope', '$state', 'MyProduct','Testproduct'];

    function MyProductController ($scope, $state, MyProduct, Testproduct) {
        var vm = this;
                vm.testproducts = Testproduct.query();

                vm.myProducts = [];


                 loadAll();

                 function loadAll() {
                     MyProduct.query(function(result) {
                         vm.myProducts = result;
                     });
                 }
    }
})();
