import {Drawer} from 'react-md';
import PropTypes from 'prop-types';


class BottomDrawer extends Drawer{

    static get propTypes() {
        let props = Drawer.propTypes;
        props.position = PropTypes.oneOf(['left', 'right', 'bottom']).isRequired;
        return props;
    }

    constructor(props){
        Drawer.defaultProps.position = 'bottom';
        super(props);
    }

    render(){
        return super.render()
    }
}

export default BottomDrawer;