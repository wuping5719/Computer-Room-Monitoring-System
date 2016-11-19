@echo -----------------------------------------------------------------------------
@echo PRODUCTION
@echo -----------------------------------------------------------------------------
mvn clean deploy -Dmaven.test.skip=true -Pprodjuction
@pause