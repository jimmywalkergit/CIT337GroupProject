(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCustomerDialogController', MyCustomerDialogController);

    MyCustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MyCustomer', 'MyCart', 'MyOrders'];

    function MyCustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MyCustomer, MyCart, MyOrders) {
        var vm = this;

        vm.myCustomer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ownscarts = MyCart.query({filter: 'mycustomer-is-null'});
        $q.all([vm.myCustomer.$promise, vm.ownscarts.$promise]).then(function() {
            if (!vm.myCustomer.ownscartId) {
                return $q.reject();
            }
            return MyCart.get({id : vm.myCustomer.ownscartId}).$promise;
        }).then(function(ownscart) {
            vm.ownscarts.push(ownscart);
        });
        vm.myorders = MyOrders.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.myCustomer.id !== null) {
                MyCustomer.update(vm.myCustomer, onSaveSuccess, onSaveError);
            } else {
                MyCustomer.save(vm.myCustomer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:myCustomerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
