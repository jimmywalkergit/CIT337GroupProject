(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('CustomerDialogController', CustomerDialogController);

    CustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Customer', 'Cart', 'Orders'];

    function CustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Customer, Cart, Orders) {
        var vm = this;

        vm.customer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.have = Cart.query({filter: 'customer-is-null'});
        $q.all([vm.customer.$promise, vm.have.$promise]).then(function() {
            if (!vm.customer.hasId) {
                return $q.reject();
            }
            return Cart.get({id : vm.customer.hasId}).$promise;
        }).then(function(has) {
            vm.have.push(has);
        });
        vm.orders = Orders.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customer.id !== null) {
                Customer.update(vm.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save(vm.customer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:customerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
