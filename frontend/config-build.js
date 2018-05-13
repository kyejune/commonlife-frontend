const fs = require( 'fs' );

if( fs.existsSync( 'src/config/config.js' ) ) {
	fs.unlinkSync( 'src/config/config.js' );
}
fs.copyFileSync( 'src/config/config.build.js', 'src/config/config.js' );