(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestproductController', TestproductController);

    TestproductController.$inject = ['$scope', '$state', 'Testproduct'];

    function TestproductController ($scope, $state, Testproduct) {
        var vm = this;
        
        vm.testproducts = [];

        loadAll();

        function loadAll() {
            Testproduct.query(function(result) {
                vm.testproducts = result;
            });
        }
    }
})();
