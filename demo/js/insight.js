var NUMBER_OF_COMPANIES = 500;
var eventsReceived = 0;

var PAGE_SIZE = 100;

var rows = [];
var rowsSorted = [];

var companyNames = ['SOFTWARE AG','Abbott Laboratories','AbbVie','Accenture plc','ACE Limited','Activision Blizzard','Adobe Systems Inc','ADT Corp','Advance Auto Parts','AES Corp','Aetna Inc','AFLAC Inc','Affiliated Managers Group Inc','Agilent Technologies Inc','AGL Resources Inc.','Air Products & Chemicals Inc','Airgas Inc','Akamai Technologies Inc','Alcoa Inc','Allergan plc','Alexion Pharmaceuticals','Allegion','Alliance Data Systems','Allstate Corp','Alphabet Inc Class A','Alphabet Inc Class C','Altera Corp','Altria Group Inc','Amazon.com Inc','Ameren Corp','American Airlines Group','American Electric Power','American Express Co','American International Group, Inc.','American Tower Corp A','Ameriprise Financial','AmerisourceBergen Corp','Ametek','Amgen Inc','Amphenol Corp A','Anadarko Petroleum Corp','Analog Devices, Inc.','Aon plc','Apache Corporation','Apartment Investment & Mgmt','Apple Inc.','Applied Materials Inc','Archer-Daniels-Midland Co','Assurant Inc','AT&T Inc','Autodesk Inc','Automatic Data Processing','AutoNation Inc','AutoZone Inc','Avago Technologies','AvalonBay Communities, Inc.','Avery Dennison Corp','Baker Hughes Inc','Ball Corp','Bank of America Corp','The Bank of New York Mellon Corp.','Bard (C.R.) Inc.','Baxalta','Baxter International Inc.','BB&T Corporation','Becton Dickinson','Bed Bath & Beyond','Berkshire Hathaway','Best Buy Co. Inc.','BIOGEN IDEC Inc.','BlackRock','Block H&R','Boeing Company','BorgWarner','Boston Properties','Boston Scientific','Bristol-Myers Squibb','Broadcom Corporation','Brown-Forman Corporation','C. H. Robinson Worldwide','CA, Inc.','Cablevision Systems Corp.','Cabot Oil & Gas','Cameron International Corp.','Campbell Soup','Capital One Financial','Cardinal Health Inc.','Henry Schein','Carmax Inc','Carnival Corp.','Caterpillar Inc.','CBRE Group','CBS Corp.','Celgene Corp.','CenterPoint Energy','CenturyLink Inc','Cerner','CF Industries Holdings Inc','Charles Schwab Corporation','Chesapeake Energy','Chevron Corp.','Chipotle Mexican Grill','Chubb Corp.','CIGNA Corp.','Cimarex Energy','Cincinnati Financial','Cintas Corporation','Cisco Systems','Citigroup Inc.','Citrix Systems','The Clorox Company','CME Group Inc.','CMS Energy','Coach Inc.','The Coca Cola Company','Coca-Cola Enterprises','Cognizant Technology Solutions','Colgate-Palmolive','Columbia Pipeline Group Inc','Comcast A Corp','Comcast Special Corp Class A','Comerica Inc.','ConAgra Foods Inc.','ConocoPhillips','CONSOL Energy Inc.','Consolidated Edison','Constellation Brands','Corning Inc.','Costco Co.','Crown Castle International Corp.','CSRA Inc.','CSX Corp.','Cummins Inc.','CVS Caremark Corp.','D. R. Horton','Danaher Corp.','Darden Restaurants','DaVita Inc.','Deere & Co.','Delphi Automotive','Delta Air Lines','Dentsply International','Devon Energy Corp.','Diamond Offshore Drilling','Discover Financial Services','Discovery Communications-A','Discovery Communications-C','Dollar General','Dollar Tree','Dominion Resources','Dover Corp.','Dow Chemical','Dr Pepper Snapple Group','DTE Energy Co.','Du Pont (E.I.)','Duke Energy','Dun & Bradstreet','E*Trade','Eastman Chemical','Eaton Corporation','eBay Inc.','Ecolab Inc.','Edison Intl','Edwards Lifesciences','Electronic Arts','EMC Corp.','Emerson Electric Company','Endo International','Ensco','Entergy Corp.','EOG Resources','EQT Corporation','Equifax Inc.','Equinix','Equity Residential','Essex Property Trust Inc','Estee Lauder Cos.','Eversource Energy','Exelon Corp.','Expedia Inc.','Expeditors Intl','Express Scripts','Exxon Mobil Corp.','F5 Networks','Facebook','Fastenal Co','FedEx Corporation','Fidelity National Information Services','Fifth Third Bancorp','First Solar Inc','FirstEnergy Corp','Fiserv Inc','FLIR Systems','Flowserve Corporation','Fluor Corp.','FMC Corporation','FMC Technologies Inc.','Ford Motor','Fossil, Inc.','Franklin Resources','Freeport-McMoran Cp & Gld','Frontier Communications','GameStop Corp.','Gap (The)','Garmin Ltd.','General Dynamics','General Electric','General Growth Properties Inc.','General Mills','General Motors','Genuine Parts','Gilead Sciences','Goldman Sachs Group','Goodyear Tire & Rubber','Grainger (W.W.) Inc.','Halliburton Co.','Hanesbrands Inc','Harley-Davidson','Harman Intl Industries','Harris Corporation','Hartford Financial Svc.Gp.','Hasbro Inc.','HCA Holdings','HCP Inc.','Helmerich & Payne','Hess Corporation','Hewlett Packard Enterprise','Home Depot','Honeywell Intl Inc.','Hormel Foods Corp.','Host Hotels & Resorts','HP Inc.','Humana Inc.','Huntington Bancshares','Illinois Tool Works','Illumina Inc','Ingersoll-Rand PLC','Intel Corp.','Intercontinental Exchange','International Bus. Machines','International Paper','Interpublic Group','Intl Flavors & Fragrances','Intuit Inc.','Intuitive Surgical Inc.','Invesco Ltd.','Iron Mountain Incorporated','Jacobs Engineering Group','J. B. Hunt Transport Services','Johnson & Johnson','Johnson Controls','JPMorgan Chase & Co.','Juniper Networks','Kansas City Southern','Kellogg Co.','KeyCorp','Keurig Green Mountain','Kimberly-Clark','Kimco Realty','Kinder Morgan','KLA-Tencor Corp.','Kohls Corp.','Kraft Heinz Co','Kroger Co.','L Brands Inc.','L-3 Communications Holdings','Laboratory Corp. of America Holding','Lam Research','Legg Mason','Leggett & Platt','Lennar Corp.','Level 3 Communications','Leucadia National Corp.','Lilly (Eli) & Co.','Lincoln National','Linear Technology Corp.','Lockheed Martin Corp.','Loews Corp.','Lowes Cos.','LyondellBasell','M&T Bank Corp.','Macerich','Macys Inc.','Mallinckrodt Plc','Marathon Oil Corp.','Marathon Petroleum','Marriott Intl.','Marsh & McLennan','Martin Marietta Materials','Masco Corp.','Mastercard Inc.','Mattel Inc.','McCormick & Co.','McDonalds Corp.','McGraw Hill Financial','McKesson Corp.','Mead Johnson','Westrock Co','Medtronic plc','Merck & Co.','MetLife Inc.','Michael Kors Holdings','Microchip Technology','Micron Technology','Microsoft Corp.','Mohawk Industries','Molson Coors Brewing Company','Mondelez International','Monsanto Co.','Monster Beverage','Moodys Corp','Morgan Stanley','The Mosaic Company','Motorola Solutions Inc.','Murphy Oil','Mylan N.V.','NASDAQ OMX Group','National Oilwell Varco Inc.','Navient','NetApp','Netflix Inc.','Newell Rubbermaid Co.','Newfield Exploration Co','Newmont Mining Corp. (Hldg. Co.)','News Corp. Class A','News Corp. Class B','NextEra Energy','Nielsen Holdings','Nike','NiSource Inc.','Noble Energy Inc','Nordstrom','Norfolk Southern Corp.','Northern Trust Corp.','Northrop Grumman Corp.','NRG Energy','Nucor Corp.','Nvidia Corporation','OReilly Automotive','Occidental Petroleum','Omnicom Group','ONEOK','Oracle Corp.','Owens-Illinois Inc','PACCAR Inc.','Parker-Hannifin','Patterson Companies','Paychex Inc.','PayPal','Pentair Ltd.','Peoples United Financial','Pepco Holdings Inc.','PepsiCo Inc.','PerkinElmer','Perrigo','Pfizer Inc.','PG&E Corp.','Philip Morris International','Phillips 66','Pinnacle West Capital','Pioneer Natural Resources','Pitney-Bowes','Plum Creek Timber Co.','PNC Financial Services','Polo Ralph Lauren Corp.','PPG Industries','PPL Corp.','Praxair Inc.','Precision Castparts','Priceline.com Inc','Principal Financial Group','Procter & Gamble','Progressive Corp.','Prologis','Prudential Financial','Public Serv. Enterprise Inc.','Public Storage','Pulte Homes Inc.','PVH Corp.','Qorvo','Quanta Services Inc.','QUALCOMM Inc.','Quest Diagnostics','Range Resources Corp.','Raytheon Co.','Realty Income Corporation','Red Hat Inc.','Regeneron','Regions Financial Corp.','Republic Services Inc','Reynolds American Inc.','Robert Half International','Rockwell Automation Inc.','Rockwell Collins','Roper Industries','Ross Stores','Royal Caribbean Cruises Ltd','Ryder System','Salesforce.com','SanDisk Corporation','SCANA Corp','Schlumberger Ltd.','Scripps Networks Interactive Inc.','Seagate Technology','Sealed Air Corp.(New)','Sempra Energy','Sherwin-Williams','Signet Jewelers','Simon Property Group Inc','Skyworks Solutions','SL Green Realty','Smucker (J.M.)','Snap-On Inc.','Southern Co.','Southwest Airlines','Southwestern Energy','Spectra Energy Corp.','St Jude Medical','Stanley Black & Decker','Staples Inc.','Starbucks Corp.','Starwood Hotels & Resorts','State Street Corp.','Stericycle Inc','Stryker Corp.','SunTrust Banks','Symantec Corp.','Synchrony Financial','Sysco Corp.','T. Rowe Price Group','Target Corp.','TE Connectivity Ltd.','TECO Energy','Tegna','Tenet Healthcare Corp.','Teradata Corp.','Tesoro Petroleum Co.','Texas Instruments','Textron Inc.','The Hershey Company','The Travelers Companies Inc.','Thermo Fisher Scientific','Tiffany & Co.','Time Warner Inc.','Time Warner Cable Inc.','TJX Companies Inc.','Torchmark Corp.','Total System Services','Tractor Supply Company','Transocean','TripAdvisor','Twenty-First Century Fox Class A','Twenty-First Century Fox Class B','Tyson Foods','Tyco International','U.S. Bancorp','Under Armour','Union Pacific','United Continental Holdings','United Health Group Inc.','United Parcel Service','United Rentals, Inc.','United Technologies','Universal Health Services, Inc.','Unum Group','Urban Outfitters','V.F. Corp.','Valero Energy','Varian Medical Systems','Ventas Inc','Verisign Inc.','Verisk Analytics','Verizon Communications','Vertex Pharmaceuticals Inc','Viacom Inc.','Visa Inc.','Vornado Realty Trust','Vulcan Materials','Wal-Mart Stores','Walgreens Boots Alliance','The Walt Disney Company','Waste Management Inc.','Waters Corporation','Anthem Inc.','Wells Fargo','Welltower Inc.','Western Digital','Western Union Co','Weyerhaeuser Corp.','Whirlpool Corp.','Whole Foods Market','Williams Cos.','Wisconsin Energy Corporation','Wyndham Worldwide','Wynn Resorts Ltd','Xcel Energy Inc','Xerox Corp.','Xilinx Inc','XL Capital','Xylem Inc.','Yahoo Inc.','Yum! Brands Inc','Zimmer Biomet Holdings','Zions Bancorp','Zoetis'];

var session = Nirvana.createSession();
session.start();

var channel = session.getChannel("correlationOutput");
channel.on(Nirvana.Observe.DATA, function(evt) {
	rows.push(evt.getDictionary().get("row"));
	rowsSorted.push(evt.getDictionary().get("row").slice(0).sort(function(a, b) { return b - a; }));
	console.log(rows[eventsReceived]);

	eventsReceived++;

	if (eventsReceived == NUMBER_OF_COMPANIES) {
		populateCorrelationPanel();
	}
});
channel.subscribe();

function populateCorrelationPanel() {
	var innerHTML = "";
	var i = 0;
	for (i = 0; i < rows.length; i++) {
		innerHTML += '<a onclick="populateCorrelationsList(' + i + ')" style="cursor: pointer;">';
		innerHTML += companyNames[i];
		innerHTML += '</a><br/>';
	}

	$(".throbber").hide();
	$(".correlationsPanel .companies .overview").html(innerHTML);
}

function populateCorrelationsList(company) {
	$(".correlationsPanel .correlations .selectedCompany").html(companyNames[company]);
	$(".correlationsPanel .correlations .correlatesText").css('display', 'block');

	var innerHTML = '<table class="correlationsPairsTable">';
	var i = 0;
	for (i = 0; i < PAGE_SIZE; i++) {
		innerHTML += '<tr>';
		if (rowsSorted[company][i] > 1 || rowsSorted[company][i] < -1) {
			continue;
		}

		var companyIndex = rows[company].indexOf(rowsSorted[company][i]);
		var companyName = companyNames[companyIndex];

		innerHTML+= '<td>' + companyNames[company] + '</td>';
		innerHTML+= '<td>' + companyName + '</td>';
		innerHTML+= '<td>' + rowsSorted[company][i].toFixed(2) + '</td>';
		innerHTML += '</tr>';
	}

	innerHTML += '</table>';
	$(".correlationsPanel .correlations .correlationsList").html(innerHTML);
}