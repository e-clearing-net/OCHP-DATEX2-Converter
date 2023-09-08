# Introduction 
This is a converter (mapper) library to convert (map) [OCHP]((https://github.com/e-clearing-net/OCHP/blob/OCHP-1.4.1/OCHP.md)) Model to [Datex II](https://datex2.eu/) 3.2 Model for publishing.

Some of the mapping is done with the help of [mapstruct](https://mapstruct.org/). \
**mapstruct** generates code on compile time.

Main OCHP Models are ChargePointInfo, TariffInfo and EvseStatusType (Live-Status). 

Technical documentation:
- [Datex II UML](https://docs.datex2.eu/_static/data/v3.2/umlmodel/html/index.htm)
- [Datex II Energy and ElectricChargingPoint](https://docs.datex2.eu/energy/)
- [DATEX II Spannende Daten kompliziert verpackt - German](https://binary-butterfly.de/artikel/datex-ii-spannende-daten-kompliziert-verpackt/)
 

# Getting Started
All mapping classes are located in the package *de.now.mapper.ochp*. \
Datex II and OCHP schemas (.xsd) are located in *resources/schemas/*.\
Classes are generated from the schema.

The methods in EnergyInfrastructureMapper are not setting all fields:
- EnergyInfrastructureStation.refillPoint (ElectricChargingPoint) needs to be set manually (source is ChargePointInfo);
- EnergyInfrastructureSite.rates (GeneralRateInformation) needs to be set manually (source is TariffInfo);
- EnergyInfrastructureTable.energyInfrastructureSite needs to be set manually;

# Build and Test
Maven clean, compile, test, etc. \
Unit tests are included for each mapper class. \

# Datex II sample data
- [ncup.si EnergyInfrastructureTablePublication ](https://www.ncup.si/en/dataset_code?id=46963663-38dd-eb04-43a9-cca9bdc0e4ba&data=codesample)