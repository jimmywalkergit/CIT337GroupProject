(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('OrderproductDeleteController',OrderproductDeleteController);

    OrderproductDeleteController.$inject = ['$uibModalInstance', 'entity', 'Orderproduct'];

    function OrderproductDeleteController($uibModalInstance, entity, Orderproduct) {
        var vm = this;

        vm.orderproduct = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Orderproduct.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
