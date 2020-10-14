<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="quertForm" :inline="true">
        <!--TODO 按钮事件-->
        <el-form-item label="角色名称" prop="roleName">
          <el-input
            v-model="queryParams.roleName"
            placeholder="请输入角色名称"
            clearable
            size="small"
            style="width: 240px"
          />
        </el-form-item>
        <el-form-item label="权限字符" prop="roleKey">
          <el-input
            v-model="queryParams.roleKey"
            placeholder="请输入权限字符"
            clearable
            size="small"
            style="width: 240px"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="queryParams.status"
            placeholder="角色状态"
            clearable
            size="small"
            style="width: 240px"
          >
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="dateRange"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini">重置</el-button>
        </el-form-item>
      </el-form>

      <!--TODO 权限与按钮事件-->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:role:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            v-hasPermi="['system:role:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            v-hasPermi="['system:role:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            v-hasPermi="['system:post:export']"
          >导出</el-button>
        </el-col>
      </el-row>

      <!--TODO 显示表单与按钮事件-->
      <el-table v-loading="loading" :data="roleList" style="margin-top: 20px" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="角色编号" prop="id" width="120" />
        <el-table-column label="角色名称" prop="roleName" :show-overflow-tooltip="true" width="150" />
        <el-table-column label="权限字符" prop="roleKey" :show-overflow-tooltip="true" width="150" />
        <el-table-column label="显示顺序" prop="roleSort" width="100" />
        <el-table-column label="状态" align="center" width="100">
          <template slot-scope="scope">
            <span>{{scope.row.status === 1 ? '正常' : '禁用'}}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="gmtCreate" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.gmtCreate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:role:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-circle-check"
              @click="handleDataScope(scope.row)"
              v-hasPermi="['system:role:edit']"
            >数据权限</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:role:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页-->
      <el-pagination  class="app-container"
        style="text-align: right"
        v-show="total>0"
        :total="total"
        :current-page="queryParams.pageNum"
        :page-size="queryParams.pageSize"
        background
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="getList"
      />

      <!--添加/修改操作栏-->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="form.roleName" placeholder="请输入角色名称"/>
          </el-form-item>
          <el-form-item label="权限字符" prop="roleKey">
            <el-input v-model="form.roleKey" placeholder="请输入权限字符"/>
          </el-form-item>
          <el-form-item label="角色顺序" prop="roleSort">
            <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
          </el-form-item>
          <el-form-item label="状态">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{dict.dictLabel}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="菜单权限">
            <el-tree
              :data="menuOptions"
              show-checkbox
              ref="menu"
              node-key="id"
              empty-text="加载中，请稍后"
              :props="defaultProps"
            ></el-tree>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!--数据权限-->
      <el-dialog :title="title" :visible.sync="openDataScope" width="500px" append-to-body>
        <el-form :model="form" label-width="80px">
          <el-form-item label="角色名称">
            <el-input v-model="form.roleName" :disabled="true"/>
          </el-form-item>
          <el-form-item label="权限字符">
            <el-input v-model="form.roleKey" :disabled="true"/>
          </el-form-item>
          <el-form-item label="权限范围">
            <el-select v-model="form.dataScope">
              <el-option
                v-for="item in dataScopeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="数据权限" v-show="form.dataScope == 2">
            <el-tree
              :data="collegeOptions"
              show-checkbox
              ref="college"
              node-key="id"
              empty-text="加载中，请稍后"
              :props="defaultProps"
            ></el-tree>
          </el-form-item>

        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="submitDataScope">确 定</el-button>
          <el-button @click="cancelDataScope">取 消</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    import RoleApi from "@/api/system/role";
    import MenuApi from "@/api/system/menu";
    import CollegeApi from "@/api/system/college";

    export default {
      name: "index",
      data(){
          return{
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 总页数
            total: 0,
            // 操作栏标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 是否显示弹出层（数据权限）
            openDataScope: false,
            // 角色表格数据
            roleList: [],
            // 日期范围
            dateRange: [],
            // 状态数字字典
            statusOptions: [],
            // 菜单列表
            menuOptions: [],
            // 学院列表
            collegeOptions: [],
            // 查询参数
            queryParams:{
              pageNum: 1,
              pageSize: 10,
              roleName: undefined,
              roleKey: undefined,
              status: undefined
            },
            // 表单参数
            form: {},
            defaultProps: {
              children: "children",
              label: "label"
            },
            // 数据范围选项
            dataScopeOptions: [
              {
                value: "1",
                label: "全部数据权限"
              },
              {
                value: "2",
                label: "自定数据权限"
              },
              {
                value: "3",
                label: "本部门数据权限"
              },
              {
                value: "4",
                label: "本部门及以下数据权限"
              },
              {
                value: "5",
                label: "仅本人数据权限"
              }
            ],
            // 规则
            rules: {
              roleName: [
                { required: true, message: "角色名称不能为空", trigger: "blur" }
              ],
              roleKey: [
                { required: true, message: "权限字符不能为空", trigger: "blur" }
              ],
              roleSort: [
                { required: true, message: "角色顺序不能为空", trigger: "blur" }
              ]
            }
          }
      },

      created() {
        this.getList();
        this.getDicts("sys_normal_disable").then(response =>{
          this.statusOptions = response.data.list;
        })
      },

      methods:{
        /** 获取分页列表*/
        getList(pageNum = 1){
          this.queryParams.pageNum = pageNum;
          this.loading = true;
          RoleApi.listRole(this.addDateRange(this.queryParams, this.dateRange)).then(response =>{
            this.roleList = response.rows;
            this.total = response.total;
            this.loading = false;
          })
        },

        // 新增or修改撤销
        cancel() {
          this.open = false;
          this.reset();
        },

        // 撤销数据权限分配
        cancelDataScope() {
          this.openDataScope = false;
          this.reset();
        },

        // 多选框选中数据
        handleSelectionChange(selection){
          this.ids = selection.map(item => item.id);
          this.single = selection.length!=1
          this.multiple = !selection.length
        },

        /** 查询菜单树结构*/
        getMenuTreeSelect(){
          MenuApi.treeselect().then(response =>{
            this.menuOptions = response.data.menuTree;
            console.log(response.data.menuTree);
          })
        },

        /** 查询角色id查询部门树结构*/
        getRoleCollegeTreeselect(id){
          return CollegeApi.roleCollegeTreeselect(id).then(response =>{
            this.collegeOptions = response.data.colleges;
            return response;
          })
        },

        /** 根据角色ID查询菜单树*/
        getRoleMenuTreeselect(id){
          return MenuApi.roleMenuTreeselect(id).then(response =>{
            this.menuOptions = response.data.menus;
            return response;
          })
        },

        /** 所有菜单结点数据*/
        getMenuAllCheckedKeys(){
          // 目前被选中的菜单节点
          let checkedKeys = this.$refs.menu.getHalfCheckedKeys();
          // 半选中的菜单节点
          let halfCheckedKeys = this.$refs.menu.getCheckedKeys();
          checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
          return checkedKeys;
        },

        /** 所有学院节点数据*/
        getCollegeAllCheckedKeys(){
          // 目前被选中的学院节点
          let checkedKeys = this.$refs.college.getHalfCheckedKeys();
          // 半选中的学院节点
          let halfCheckedKeys = this.$refs.college.getCheckedKeys();
          checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
          return checkedKeys;
        },

        // 表单重置
        reset(){
          if (this.$refs.menu != undefined) {
            this.$refs.menu.setCheckedKeys([]);
          }

          this.form = {
            id: undefined,
            roleName: undefined,
            roleKey: undefined,
            roleSort: 0,
            status: 1,
            menuIds: [],
            collegeIds: [],
            remark: undefined
          };
          this.resetForm("form");
        },

        /** 新增按钮*/
        handleAdd(){
          this.reset();
          this.getMenuTreeSelect();
          this.open = true;
          this.title="添加角色";
        },

        /** 修改按钮*/
        handleUpdate(row){
          this.reset();
          const roleId = row.id || this.ids;
          console.log(roleId);
          const roleMenu = this.getRoleMenuTreeselect(roleId);
          RoleApi.getRole(roleId).then(response =>{
            this.form = response.data.role;
            this.open = true;
            this.$nextTick(() =>{
              roleMenu.then(res =>{
                this.$refs.menu.setCheckedKeys(res.data.checkedKeys);
              });
            });
            this.title = "修改角色";
          });
        },

        /** 提交按钮*/
        submitForm:function () {
          this.$refs["form"].validate(valid =>{
            if(valid){
              if(this.form.id != undefined){
                this.form.menuIds = this.getMenuAllCheckedKeys();
                RoleApi.updateRole(this.form).then(response =>{
                  if(response.code === 0){
                    this.$notify({type: 'success',message: '修改角色信息成功!'});
                    this.open = false;
                    this.getList();
                  }
                });
              }else{
                this.form.menuIds = this.getMenuAllCheckedKeys();
                RoleApi.addRole(this.form).then(response =>{
                  if(response.code === 0){
                    this.$notify({type: 'success',message: '新增角色信息成功!'});
                    this.open = false;
                    this.getList();
                  }
                })
              }
            }
          })
        },

        /** 分配数据权限操作*/
        handleDataScope(row){
          this.reset();
          const roleCollegeTreeselect = this.getRoleCollegeTreeselect(row.id);
          RoleApi.getRole(row.id).then(response =>{
            this.form = response.data.role;
            this.openDataScope = true;
            this.$nextTick(() =>{
              roleCollegeTreeselect.then(res =>{
                this.$refs.college.setCheckedKeys(res.data.checkedKeys);
              });
            });
            this.title = "分配数据权限";
          });
        },

        /** 提交按钮（数据权限）*/
        submitDataScope: function () {
          if(this.form.id != undefined){
            this.form.collegeIds = this.getCollegeAllCheckedKeys();
            RoleApi.dataScope(this.form).then(response =>{
              if(response.code === 0){
                this.$notify({type: 'success',message: '修改角色数据权限成功!'});
                this.openDataScope = false;
                this.getList();
              }
            });
          }
        },

        /** 删除按钮操作*/
        handleDelete(row){
          const roleIds = row.id || this.ids;
          this.$confirm('是否确认删除角色编号为"' + roleIds + '"的数据项?' , "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function () {
            return RoleApi.delRole(roleIds);
          }).then(() =>{
            this.getList();
            this.$notify({type: 'success',message: '删除角色数据权限成功!'});
          }).catch(function () {})
        }



      }
    }
</script>

<style scoped>

</style>
