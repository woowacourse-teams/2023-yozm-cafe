"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[699],{"./src/components/CafeDetailBottomSheet.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return CafeDetailBottomSheet_stories}});var mockData=__webpack_require__("./src/data/mockData.ts"),react=__webpack_require__("./node_modules/react/index.js"),index_esm=__webpack_require__("./node_modules/react-icons/bs/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),client=__webpack_require__("./src/client.ts"),useSuspenseQuery=__webpack_require__("./src/hooks/useSuspenseQuery.ts");var hooks_useCafeMenus=cafeId=>(0,useSuspenseQuery.Z)({queryKey:["cafes",cafeId,"menus"],queryFn:()=>client.Z.getCafeMenu(cafeId)}),useScrollSnapGuard=__webpack_require__("./src/hooks/useScrollSnapGuard.ts"),CafeMenuMiniList=__webpack_require__("./src/components/CafeMenuMiniList.tsx"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const DAY_MAPPER={MONDAY:"월",TUESDAY:"화",WEDNESDAY:"수",THURSDAY:"목",FRIDAY:"금",SATURDAY:"토",SUNDAY:"일"},OpeningHoursDetail=props=>{const{openingHours:openingHours}=props,today=(new Date).toLocaleDateString("en-US",{weekday:"long"}).toUpperCase(),todayOpeningHour=openingHours.find((openingHour=>openingHour.day===today))??null;return(0,jsx_runtime.jsxs)(Container,{"aria-live":"polite",children:[(0,jsx_runtime.jsxs)(SummaryDetails,{children:[(0,jsx_runtime.jsx)(index_esm.ZEP,{}),(0,jsx_runtime.jsx)("h3",{children:(()=>{const currentTime=(()=>{const now=new Date;return`${now.getHours().toString().padStart(2,"0")}:${now.getMinutes().toString().padStart(2,"0")}`})();return!(!todayOpeningHour||!todayOpeningHour.opened)&&(today===todayOpeningHour.day&&todayOpeningHour.open<=currentTime&&currentTime<=todayOpeningHour.close)})()?"영업중":"영업 종료"}),(0,jsx_runtime.jsx)(Summary,{children:todayOpeningHour?.opened?`${DAY_MAPPER[todayOpeningHour.day]} ${todayOpeningHour.open} - ${todayOpeningHour.close}`:"휴무"})]}),(0,jsx_runtime.jsx)(Details,{children:openingHours.map((openingHour=>(0,jsx_runtime.jsxs)("li",{children:[DAY_MAPPER[openingHour.day]," ",openingHour.opened?`${openingHour.open} - ${openingHour.close}`:"휴무"]},openingHour.day)))})]})};OpeningHoursDetail.displayName="OpeningHoursDetail";var components_OpeningHoursDetail=OpeningHoursDetail;const Container=styled_components_browser_esm.zo.div``,Summary=styled_components_browser_esm.zo.h3``,Details=styled_components_browser_esm.zo.ul`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[1]};
  align-items: flex-end;

  color: ${({theme:theme})=>theme.color.gray};
`,SummaryDetails=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme:theme})=>theme.space[2]};
  align-items: center;
  margin-bottom: ${({theme:theme})=>theme.space[1]};
`;try{OpeningHoursDetail.displayName="OpeningHoursDetail",OpeningHoursDetail.__docgenInfo={description:"",displayName:"OpeningHoursDetail",props:{openingHours:{defaultValue:null,description:"",name:"openingHours",required:!0,type:{name:"OpeningHour[]"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/OpeningHoursDetail.tsx#OpeningHoursDetail"]={docgenInfo:OpeningHoursDetail.__docgenInfo,name:"OpeningHoursDetail",path:"src/components/OpeningHoursDetail.tsx#OpeningHoursDetail"})}catch(__react_docgen_typescript_loader_error){}const CafeDetailBottomSheet=props=>{const{cafe:cafe,onClose:onClose}=props,scrollSnapGuardHandlers=(0,useScrollSnapGuard.Z)();(0,react.useEffect)((()=>(document.addEventListener("click",onClose),()=>document.removeEventListener("click",onClose))),[onClose]);return(0,jsx_runtime.jsxs)(CafeDetailBottomSheet_Container,{onClick:event=>{event.stopPropagation()},role:"dialog","aria-modal":"true",...scrollSnapGuardHandlers,children:[(0,jsx_runtime.jsx)(CloseButton,{children:(0,jsx_runtime.jsx)(CloseIcon,{onClick:onClose})}),(0,jsx_runtime.jsx)(Title,{children:cafe.name}),(0,jsx_runtime.jsx)(Spacer,{$size:"4"}),(0,jsx_runtime.jsx)(react.Suspense,{children:(0,jsx_runtime.jsx)(CafeMenu,{cafeId:cafe.id})}),(0,jsx_runtime.jsxs)(InfoContainer,{children:[(0,jsx_runtime.jsxs)(LocationDetail,{children:[(0,jsx_runtime.jsx)(NaverMapIcon,{}),(0,jsx_runtime.jsx)("a",{href:cafe.detail.mapUrl,target:"_blank",rel:"noopener noreferrer",children:(0,jsx_runtime.jsx)(LocationName,{children:cafe.address})})]}),(0,jsx_runtime.jsx)(OpeningHoursDetails,{children:(0,jsx_runtime.jsx)(components_OpeningHoursDetail,{openingHours:cafe.detail.openingHours})})]}),(0,jsx_runtime.jsx)(Spacer,{$size:"6"}),(0,jsx_runtime.jsx)(Content,{children:cafe.detail.description.split("\n").map(((paragraph,index)=>(0,jsx_runtime.jsx)("p",{children:paragraph},index)))}),(0,jsx_runtime.jsx)(MoreContentHintGradient,{})]})};CafeDetailBottomSheet.displayName="CafeDetailBottomSheet";var components_CafeDetailBottomSheet=CafeDetailBottomSheet;const CafeDetailBottomSheet_Container=styled_components_browser_esm.zo.div`
  position: absolute;
  bottom: 0;

  overflow-y: auto;
  display: flex;
  flex-direction: column;

  width: 100%;
  height: 450px;
  padding: ${({theme:theme})=>theme.space[4]};
  padding-bottom: 0;

  color: ${({theme:theme})=>theme.color.text.primary};
  text-shadow: none;

  background: ${({theme:theme})=>theme.color.white};

  & svg {
    filter: none !important;
  }
`,Spacer=styled_components_browser_esm.zo.div`
  min-height: ${({theme:theme,$size:$size})=>theme.space[$size]};
`,Title=styled_components_browser_esm.zo.h1`
  font-size: ${({theme:theme})=>theme.fontSize["3xl"]};
  font-weight: bolder;
`,InfoContainer=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[2]};
`,OpeningHoursDetails=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme:theme})=>theme.space[2]};
  align-items: flex-start;
`,LocationDetail=styled_components_browser_esm.zo.div`
  display: flex;
  gap: ${({theme:theme})=>theme.space[2]};
  align-items: center;
  color: ${({theme:theme})=>theme.color.secondary};
`,NaverMapIcon=styled_components_browser_esm.zo.img.attrs({src:"/assets/naver-map-icon.png",alt:"네이버 지도 아이콘"})`
  width: 16px;
  height: 16px;
`,LocationName=styled_components_browser_esm.zo.h3`
  display: flex;
  gap: ${({theme:theme})=>theme.space[1]};
  align-items: center;
`,Content=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[2]};
`,MoreContentHintGradient=styled_components_browser_esm.zo.div`
  position: sticky;
  bottom: 0;

  width: 100%;
  min-height: ${({theme:theme})=>theme.space[16]};

  background: linear-gradient(transparent, white);
`,CloseButton=styled_components_browser_esm.zo.button`
  display: flex;
  justify-content: flex-end;
  width: 100%;
`,CloseIcon=(0,styled_components_browser_esm.zo)(index_esm.z3f)`
  font-size: ${({theme:theme})=>theme.fontSize["2xl"]};
`,CafeMenu=props=>{const{cafeId:cafeId}=props,{data:{menus:menus}}=hooks_useCafeMenus(cafeId),recommendedMenus=menus.filter((menuItem=>menuItem.isRecommended));return recommendedMenus.length>0&&(0,jsx_runtime.jsxs)(jsx_runtime.Fragment,{children:[(0,jsx_runtime.jsx)(CafeMenuMiniList.Z,{menus:recommendedMenus}),(0,jsx_runtime.jsx)(Spacer,{$size:"8"})]})};try{CafeDetailBottomSheet.displayName="CafeDetailBottomSheet",CafeDetailBottomSheet.__docgenInfo={description:"",displayName:"CafeDetailBottomSheet",props:{cafe:{defaultValue:null,description:"",name:"cafe",required:!0,type:{name:"Cafe"}},onClose:{defaultValue:null,description:"",name:"onClose",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeDetailBottomSheet.tsx#CafeDetailBottomSheet"]={docgenInfo:CafeDetailBottomSheet.__docgenInfo,name:"CafeDetailBottomSheet",path:"src/components/CafeDetailBottomSheet.tsx#CafeDetailBottomSheet"})}catch(__react_docgen_typescript_loader_error){}var CafeDetailBottomSheet_stories={title:"CafeDetailBottomSheet",component:components_CafeDetailBottomSheet};const Default={args:{cafe:mockData.on[0]}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    cafe: cafes[0]\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/CafeMenuMiniList.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var react_icons_pi__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./node_modules/react-icons/pi/index.esm.js"),styled_components__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_utils_Resource__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./src/utils/Resource.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/jsx-runtime.js");const CafeMenuMiniList=props=>{const{menus:menus}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuList,{children:menus.map((menuItem=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuListItem,{menuItem:menuItem},menuItem.id)))})};CafeMenuMiniList.displayName="CafeMenuMiniList",__webpack_exports__.Z=CafeMenuMiniList;const MenuList=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.ul`
  overflow-y: auto;
  display: flex;
  flex-shrink: 0;
  gap: ${({theme:theme})=>theme.space[4]};
`,MenuListItem=props=>{const{menuItem:menuItem}=props;return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(MenuListItemContainer,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageContainer,{children:menuItem.imageUrl?(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImage,{src:_utils_Resource__WEBPACK_IMPORTED_MODULE_2__.Z.getImageUrl({size:"100",filename:menuItem.imageUrl})}):(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageAlt,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemImageAltIcon,{})})}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(MenuItemName,{children:menuItem.name})]})};MenuListItem.displayName="MenuListItem";const MenuListItemContainer=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.li`
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  align-items: center;

  width: 70px;
  height: 100%;
`,MenuItemImageContainer=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  width: 100%;
  height: 100%;
  padding: ${({theme:theme})=>theme.space[2]};

  & > * {
    aspect-ratio: 1 / 1;
    width: 100%;
  }
`,MenuItemImage=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.img`
  display: block;
  object-fit: cover;
  border-radius: 50%;
`,MenuItemImageAlt=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  display: flex;
  align-items: center;
  justify-content: center;

  background: #dedede;
  border-radius: 50%;
`,MenuItemImageAltIcon=(0,styled_components__WEBPACK_IMPORTED_MODULE_1__.zo)(react_icons_pi__WEBPACK_IMPORTED_MODULE_3__.yAJ)`
  width: 50%;
  height: 50%;
`,MenuItemName=styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.div`
  font-size: ${({theme:theme})=>theme.fontSize.xs};
`;styled_components__WEBPACK_IMPORTED_MODULE_1__.zo.hr`
  margin: ${({theme:theme})=>theme.space[2]} ${({theme:theme})=>theme.space[1]};
  border: none;
  border-left: 1px solid ${({theme:theme})=>theme.color.gray};
`;try{CafeMenuMiniList.displayName="CafeMenuMiniList",CafeMenuMiniList.__docgenInfo={description:"",displayName:"CafeMenuMiniList",props:{menus:{defaultValue:null,description:"",name:"menus",required:!0,type:{name:"CafeMenuItem[]"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeMenuMiniList.tsx#CafeMenuMiniList"]={docgenInfo:CafeMenuMiniList.__docgenInfo,name:"CafeMenuMiniList",path:"src/components/CafeMenuMiniList.tsx#CafeMenuMiniList"})}catch(__react_docgen_typescript_loader_error){}},"./src/hooks/useScrollSnapGuard.ts":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js");__webpack_exports__.Z=()=>{const preventPropagation=(0,react__WEBPACK_IMPORTED_MODULE_0__.useCallback)((event=>{event.stopPropagation()}),[]);return(0,react__WEBPACK_IMPORTED_MODULE_0__.useMemo)((()=>({onTouchStart:preventPropagation,onTouchMove:preventPropagation,onTouchEnd:preventPropagation,onMouseDown:preventPropagation,onMouseMove:preventPropagation,onMouseUp:preventPropagation,onMouseLeave:preventPropagation,onWheel:preventPropagation})),[preventPropagation])}},"./src/hooks/useSuspenseQuery.ts":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){var _tanstack_react_query__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs");__webpack_exports__.Z=function useSuspenseQuery(options){const queryResult=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_0__.a)({...options,suspense:!0});return{...queryResult,isIdle:"isIdle"in queryResult&&queryResult.isIdle}}},"./src/utils/Resource.ts":function(__unused_webpack_module,__webpack_exports__){class Resource{constructor(){}static getImageUrl(options){const{filename:filename,size:size}=options;return Resource.joinPath("/images",size,filename)}static getAssetUrl(options){const{filename:filename}=options;return Resource.joinPath("/assets",filename)}static joinPath(...paths){return paths.map(((part,index)=>(index>0&&(part=part.replace(/^\/+/,"")),index!==paths.length-1&&(part=part.replace(/\/+$/,"")),part))).join("/")}}__webpack_exports__.Z=Resource}}]);
//# sourceMappingURL=components-CafeDetailBottomSheet-stories.2ca78369.iframe.bundle.js.map