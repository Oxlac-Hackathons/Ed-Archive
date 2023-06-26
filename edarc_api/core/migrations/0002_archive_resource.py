# Generated by Django 4.2.1 on 2023-06-26 05:02

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):
    dependencies = [
        ("core", "0001_initial"),
    ]

    operations = [
        migrations.CreateModel(
            name="Archive",
            fields=[
                (
                    "id",
                    models.BigAutoField(
                        auto_created=True,
                        primary_key=True,
                        serialize=False,
                        verbose_name="ID",
                    ),
                ),
                ("name", models.CharField(max_length=100, null=True)),
                ("description", models.CharField(max_length=100, null=True)),
                ("created_at", models.DateTimeField(auto_now_add=True)),
                ("updated_at", models.DateTimeField(auto_now=True)),
            ],
        ),
        migrations.CreateModel(
            name="Resource",
            fields=[
                (
                    "id",
                    models.BigAutoField(
                        auto_created=True,
                        primary_key=True,
                        serialize=False,
                        verbose_name="ID",
                    ),
                ),
                ("name", models.CharField(max_length=100, null=True)),
                ("description", models.CharField(max_length=100, null=True)),
                ("link", models.URLField(null=True)),
                (
                    "resource_type",
                    models.CharField(
                        choices=[
                            ("video", "Video"),
                            ("article", "Article"),
                            ("book", "Book"),
                            ("research_paper", "Research Paper"),
                        ],
                        max_length=20,
                    ),
                ),
                ("extra_params", models.JSONField(blank=True, null=True)),
                ("difficulty", models.CharField(max_length=100, null=True)),
                ("created_at", models.DateTimeField(auto_now_add=True)),
                ("updated_at", models.DateTimeField(auto_now=True)),
                (
                    "archive",
                    models.ForeignKey(
                        on_delete=django.db.models.deletion.CASCADE, to="core.archive"
                    ),
                ),
            ],
        ),
    ]