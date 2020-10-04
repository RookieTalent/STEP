<template>
    <div class="app-container">
      <el-form :inline="true">
        <el-form-item label="菜单名称">
          <el-input
            v-model="queryParams.menuName"
            placeholder="请输入菜单名称"
            clearable
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="菜单状态" clearable size="small">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <!--TODO 加权限-->
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>

      <!-- 显示表单-->
      <el-table
        v-loading="loading"
        :data="menuList"
        row-key="id"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column prop="menuName" label="菜单名称" :show-overflow-tooltip="true" width="160"></el-table-column>
        <el-table-column prop="icon" label="图标" align="center" width="100">
          <template slot-scope="scope">
            <svg-icon :icon-class="scope.row.icon" />
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="排序" width="60"></el-table-column>
        <el-table-column prop="perms" label="权限标识" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="component" label="组件路径" :show-overflow-tooltip="true"></el-table-column>
        <!--TODO 格式化内容-->
        <el-table-column prop="status" label="状态" width="80"></el-table-column>
        <el-table-column label="创建时间" align="center" prop="gmtCreate">
          <template slot-scope="scope">
            <span>{{ scope.row.gmtCreate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <!--TODO 添加按钮事件与权限-->
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-plus"
              @click="handleAdd(scope.row)"
            >新增</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>


      <!--添加或修改菜单对话框-->
      <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-row>
            <el-col :span="24">
              <el-form-item label="上级菜单">
                <treeselect
                  v-model="form.parentId"
                  :options="menuOptions"
                  :normalizer="normalizer"
                  :show-count="true"
                  placeholder="选择上级菜单"
                />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="菜单类型" prop="menuType">
                <el-radio-group v-model="form.menuType">
                  <el-radio label="M">目录</el-radio>
                  <el-radio label="C">菜单</el-radio>
                  <el-radio label="F">按钮</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item v-if="form.menuType != 'F'" label="菜单图标">
                <el-popover
                  placement="bottom-start"
                  width="460"
                  trigger="click"
                  @show="$refs['iconSelect'].reset()"
                >
                  <IconSelect ref="iconSelect" @selected="selected" />
                  <el-input slot="reference" v-model="form.icon" placeholder="请选择图标">
                    <svg-icon
                      v-if="form.icon"
                      slot="prefix"
                      :icon-class="form.icon"
                      class="el-input__icon"
                      style="height: 32px;width: 16px;"
                    />
                    <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                  </el-input>
                </el-popover>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="菜单名称" prop="menuName">
                <el-input v-model="form.menuName" placeholder="请输入菜单名称"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="显示排序" prop="orderNum">
                <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="form.menuType != 'F'" label="是否外链">
                <el-radio-group v-model="form.isFrame">
                  <el-radio :label="0">否</el-radio>
                  <el-radio :label="1">是</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="form.menuType != 'F'" label="路由地址" prop="path">
                <el-input v-model="form.path" placeholder="请输入路由地址"/>
              </el-form-item>
            </el-col>
            <el-col :span="12" v-if="form.menuType == 'C'">
              <el-form-item label="组件路径" prop="component">
                <el-input v-model="form.component" placeholder="请输入组件路径"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="form.menuType != 'M'" label="权限标识">
                <el-input v-model="form.perms" placeholder="请输入权限标识" maxlength="50" />
              </el-form-item>
            </el-col>
            <!--TODO 数字字典-->
            <el-col :span="12">
              <el-form-item v-if="form.menuType != 'F'" label="显示状态">
                <el-radio-group v-model="form.visible">
                  <el-radio
                    v-for="dict in visibleOptions"
                    :key="dict.dictValue"
                    :label="dict.dictValue"
                  >{{dict.dictLabel}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="form.menuType != 'F'" label="菜单状态">
                <el-radio-group v-model="form.status">
                  <el-radio
                    v-for="dict in statusOptions"
                    :key="dict.dictValue"
                    :label="dict.dictValue"
                  >{{dict.dictLabel}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="submitForm">确定</el-button>
          <el-button @click="cancel">取消</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    import MenuApi from '@/api/system/menu'
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    import IconSelect from "@/components/IconSelect";

    export default {
      name: "index",
      components: { Treeselect, IconSelect },
      data(){
        return{
          // TODO 后端写完再改 遮罩层
          loading: true,
          // 菜单表格树数据
          menuList: [],
          // 菜单树选项
          menuOptions: [],
          // 弹出层标题
          title: "",
          // 是否显示弹出层
          open: false,
          // 查询参数
          queryParams:{
            menuName: undefined,
            visible: undefined
          },
          // 显示状态数据字典
          visibleOptions:[],
          // 菜单状态数据字典
          statusOptions:[],
          // 表单参数
          form: {},
          // 表单校验
          rules: {
            menuName: [
              { required: true, message: "菜单名称不能为空", trigger: "blur" }
            ],
            orderNum: [
              { required: true, message: "菜单顺序不能为空", trigger: "blur" }
            ],
            path: [
              { required: true, message: "路由地址不能为空", trigger: "blur" }
            ]
          }
        }
      },

      created() {
        this.getList();
        this.getDicts("sys_show_hide").then(response => {
          this.visibleOptions = response.data.list;
        });
        this.getDicts("sys_normal_disable").then(response => {
          this.statusOptions = response.data.list;
        });
      },

      methods:{
        // 搜索按钮
        handleQuery(){
          this.getList();
        },

        // 取消按钮
        cancel() {
          this.open = false;
          this.reset();
        },

        // 选择图标
        selected(name) {
          this.form.icon = name;
        },

        /** 查询菜单列表*/
        getList(){
          this.loading = true;
          MenuApi.listMenu(this.queryParams).then(response =>{
            this.menuList = this.handleTree(response.data.menus, "id");
            this.loading = false;
          })
        },

        /** 表单重置*/
        reset() {
          this.form = {
            id: undefined,
            parentId: "0",
            menuName: undefined,
            icon: undefined,
            menuType: "M",
            orderNum: undefined,
            isFrame: 0,
            visible: 1,
            status: 1
          };
          this.resetForm("form");
        },

        /** 转换菜单数据结构 */
        normalizer(node) {
          if (node.children && !node.children.length) {
            delete node.children;
          }
          return {
            id: node.id,
            label: node.menuName,
            children: node.children
          };
        },

        /** 新增按钮操作*/
        handleAdd(row){
          this.reset();
          this.getTreeselect();
          if(row != null){
            this.form.parentId = row.id;
          }
          this.open = true;
          this.title = "添加菜单";
        },

        /** 修改按钮*/
        handleUpdate(row){
          this.reset();
          this.getTreeselect();

          MenuApi.getMenu(row.id).then(response =>{
            this.form = response.data.menu;
            this.open = true;
            this.title = "修改菜单";
          });
        },

        /** 查询菜单下拉树结构*/
        getTreeselect(){
          MenuApi.listMenu().then(response =>{
            this.menuOptions =[];
            const menu = {id : '0', menuName: '主类目', children: []};
            menu.children = this.handleTree(response.data.menus, "id");
            this.menuOptions.push(menu);
          })
        },

        /** 提交按钮*/
        submitForm:function () {
          this.$refs["form"].validate(valid =>{
            if(valid){
              if(this.form.id != undefined){
                // 更新
                MenuApi.updateMenu(this.form).then(response =>{
                  if(response.code === 0){
                    this.$message({type: 'success',message: '修改菜单信息成功!'});
                    this.open = false;
                    this.getList();
                  }
                });
              }else{
                // 新增
                MenuApi.addMenu(this.form).then(response =>{
                  if(response.code === 0){
                    this.$message({type: 'success',message: '新增菜单信息成功!'});
                    this.open = false;
                    this.getList();
                  }
                });
              }
            }
          })
        },

        /** 删除按钮操作*/
        handleDelete(row) {
          this.$confirm('是否确认删除名称为"' + row.menuName + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            return MenuApi.delMenu(row.id);
          }).then(() =>{
            this.getList();
            this.$message({type: 'success',message: '删除菜单信息成功!'});
          }).catch(function () {});
        }

      }
    }
</script>

<style scoped>

</style>
