(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrdercustomerDeleteController',OrdercustomerDeleteController);

    OrdercustomerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ordercustomer'];

    function OrdercustomerDeleteController($uibModalInstance, entity, Ordercustomer) {
        var vm = this;

        vm.ordercustomer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ordercustomer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
