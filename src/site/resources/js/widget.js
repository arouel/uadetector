new TWTR.Widget({
	version : 2,
	type : 'profile',
	rpp : 4,
	interval : 30000,
	width : 'auto',
	height : 150,
	theme : {
		shell : {
			background : '#eeeeee',
			color : '#000000'
		},
		tweets : {
			background : '#eeeeee',
			color : '#222222',
			links : '#0088cc'
		}
	},
	features : {
		scrollbar : false,
		loop : false,
		live : false,
		behavior : 'all'
	}
}).render().setUser('UADetector').start();