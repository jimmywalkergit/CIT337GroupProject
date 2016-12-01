(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestorderController', TestorderController);

    TestorderController.$inject = ['$scope', '$state', 'Testorder', 'Testproduct'];


    function TestorderController ($scope, $state, Testorder, Testproduct) {
        var vm = this;

        vm.testorders = [];
        vm.testproducts = Testproduct.query();



        loadAll();

        function loadAll() {
            Testorder.query(function(result) {
                vm.testorders = result;
            });
        }
    }
})();
