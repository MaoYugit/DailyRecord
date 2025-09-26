import { IsEmail, IsNotEmpty, MinLength } from 'class-validator';

export class CreateUserDto {
  @IsNotEmpty({ message: '用户名不能为空' })
  username: string;

  @IsEmail({}, { message: '请输入有效的邮箱地址' })
  email: string;

  @MinLength(6, { message: '密码长度不能少于6位' })
  password: string;
}
