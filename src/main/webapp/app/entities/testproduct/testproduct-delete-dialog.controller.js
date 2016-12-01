(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestproductDeleteController',TestproductDeleteController);

    TestproductDeleteController.$inject = ['$uibModalInstance', 'entity', 'Testproduct'];

    function TestproductDeleteController($uibModalInstance, entity, Testproduct) {
        var vm = this;

        vm.testproduct = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Testproduct.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
