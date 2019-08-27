/**
 * 作者：阿良
 * 作用：按需要导入iview 和一些别的模块的组件
 */

import {
  Button,
  ButtonGroup,
  Table,
  Col,
  Row,
  DropdownMenu,
  DropdownItem,
  Dropdown,
  Icon,
  Menu,
  MenuItem,
  MenuGroup,
  Submenu,
  Message,
  Carousel,
  CarouselItem,
  Layout,
  Sider,
  Breadcrumb,
  BreadcrumbItem,
  Card,
  Input,
  BackTop,
  FormItem,
  Form,
  AutoComplete,
  Transfer,
  Avatar,
  Poptip,
  Modal,
  Cascader,
  CellGroup,
  Cell,
  Drawer,
  Tabs,
  TabPane,
  Page,
  Spin,
  Select,
  Option,
  Switch,
  Upload,
  InputNumber,
  Rate,
  Affix,
  CheckboxGroup,
  Checkbox,
  Tag,
  Timeline,
  TimelineItem,
  Content,
  Progress
} from 'iview';


export default Vue => {
  Vue.prototype.$message = Message;
  Vue.component('Button', Button);
  Vue.component('ButtonGroup', ButtonGroup);
  Vue.component('Table', Table);
  Vue.component('Col', Col);
  Vue.component('Row', Row);
  Vue.component('DropdownMenu', DropdownMenu);
  Vue.component('DropdownItem', DropdownItem);
  Vue.component('Dropdown', Dropdown);
  Vue.component('Icon', Icon);
  Vue.component('Menu', Menu);
  Vue.component('MenuItem', MenuItem);
  Vue.component('MenuGroup', MenuGroup);
  Vue.component('Submenu', Submenu);
  Vue.component('Carousel', Carousel);
  Vue.component('CarouselItem', CarouselItem);
  Vue.component('Layout', Layout);
  Vue.component('Sider', Sider);
  Vue.component('Breadcrumb', Breadcrumb);
  Vue.component('BreadcrumbItem', BreadcrumbItem);
  Vue.component('Card', Card);
  Vue.component('Input', Input);
  Vue.component('BackTop', BackTop);
  Vue.component('FormItem', FormItem);
  Vue.component('Form', Form);
  Vue.component('AutoComplete', AutoComplete);
  Vue.component('Transfer', Transfer);
  Vue.component('Avatar', Avatar);
  Vue.component('Poptip', Poptip);
  Vue.component('Modal', Modal);
  Vue.component('Cascader', Cascader);
  Vue.component('CellGroup', CellGroup);
  Vue.component('Cell', Cell);
  Vue.component('Drawer', Drawer);
  Vue.component('Tabs', Tabs);
  Vue.component('TabPane', TabPane);
  Vue.component('Page', Page);
  Vue.component('Spin', Spin);
  Vue.component('Select', Select);
  Vue.component('Option', Option);
  Vue.component('i-switch', Switch);
  Vue.component('Upload', Upload);
  Vue.component('InputNumber', InputNumber);
  Vue.component('Rate', Rate);
  Vue.component('Affix', Affix);
  Vue.component('CheckboxGroup', CheckboxGroup);
  Vue.component('Checkbox', Checkbox);
  Vue.component('Tag', Tag);
  Vue.component('Timeline', Timeline);
  Vue.component('TimelineItem', TimelineItem);
  Vue.component('Content', Content);
  Vue.component('Progress', Progress);
};
// 这里面默认导出方法 调用他的页面可采用 improt a from xxx a就是默认的方法
